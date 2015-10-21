package by.auto.persistence.repository.dependency;

import com.googlecode.flyway.core.util.StringUtils;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.mapping.event.AbstractDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.data.mongodb.core.mapping.event.MongoMappingEvent;
import org.springframework.data.repository.support.Repositories;
import by.auto.domain.common.DomainObject;
import by.auto.domain.dependency.DependencyDomain;
import by.auto.domain.dependency.DependencyDomainHandler;

import javax.inject.Inject;


public class DependencyListener extends AbstractMongoEventListener<DomainObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyListener.class);

    @Inject
    private ApplicationContext appContext;

    private Class<? extends DomainObject> eventDomainType = null;

    private DomainObject sourceBefore;

    @Override
    public void onApplicationEvent(final MongoMappingEvent<?> event) {
        eventDomainType = null;

        if (event instanceof AbstractDeleteEvent) {
            eventDomainType = ((AbstractDeleteEvent) event).getType();
        } else if (!(event instanceof AfterLoadEvent)) {
            eventDomainType = (Class<? extends DomainObject>) event.getSource().getClass();
        }

        if (isDependency(eventDomainType)) {
            super.onApplicationEvent(event);
        }
    }

    @Override
    public void onBeforeSave(final DomainObject source, final DBObject dbo) {
        if (StringUtils.hasText(source.getId())) {
            sourceBefore = new Repositories(appContext).getCrudInvoker(source.getClass()).invokeFindOne(source.getId());
        } else {
            sourceBefore = null;
        }
        super.onBeforeSave(source, dbo);
    }

    @Override
    public void onAfterSave(final DomainObject source, final DBObject dbo) {
        if (source != null) {
            final DependencyDomainHandler handler = getHandler(source.getClass());
            if (handler != null) {
                handler.resolveSaveOrUpdate(source, sourceBefore);
            }
            super.onAfterSave(source, dbo);
        }
    }

    @Override
    public void onBeforeDelete(final DBObject dbo) {
        if (eventDomainType != null) {
            final DependencyDomainHandler handler = getHandler(eventDomainType);
            if (handler != null) {
                handler.resolveDelete(dbo.containsField("id") ? (String) dbo.get("id") : null, eventDomainType);
            }
            super.onBeforeDelete(dbo);
        }
    }

    private DependencyDomainHandler getHandler(final Class<? extends DomainObject> clazz) {
        DependencyDomainHandler handler = null;
        if (isDependency(clazz)) {
            final String cls = clazz.getAnnotation(DependencyDomain.class).dependencyResolver();
            handler = (DependencyDomainHandler) appContext.getBean(cls);
        }
        return handler;
    }

    private boolean isDependency(final Class clazz) {
        return clazz != null && clazz.isAnnotationPresent(DependencyDomain.class);
    }
}
