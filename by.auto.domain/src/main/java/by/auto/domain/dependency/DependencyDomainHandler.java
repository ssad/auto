package by.auto.domain.dependency;

import by.auto.domain.common.DomainObject;

public abstract class DependencyDomainHandler<T extends DomainObject> {
    /**
     * Override to add logic
     *
     * @param source
     * @throws Throwable
     */
    public void resolveSaveOrUpdate(final T source, final T sourceBefore) {
        //doNothing
    }

    /**
     * @param domainId id of object to delete
     * @throws Throwable
     */
    public void resolveDelete(final String domainId, final Class<T> clazz) {
        //do nothing
    }

}
