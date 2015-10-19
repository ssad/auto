<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="tabs-container" id="profileForm">
    <div class="tabs-left">
        <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#account" aria-expanded="true">Аккаунт</a></li>
            <li class=""><a data-toggle="tab" href="#profile" aria-expanded="false">Профайл</a></li>
            <li class=""><a data-toggle="tab" href="#calendar" aria-expanded="false">Календарь</a></li>
            <li class=""><a data-toggle="tab" href="#statistics" aria-expanded="false">Статистика</a></li>
            <li class=""><a data-toggle="tab" href="#notification" aria-expanded="false">Уведомления</a></li>
        </ul>
        <div class="tab-content ">
            <div id="account" class="tab-pane active">
                <div class="panel-body" style="background-color: #f3f3f4">
                    <strong>Тут аккаунт</strong>
                </div>
            </div>
            <div id="profile" class="tab-pane">
                <div class="panel-body">
                    <strong>Тут профайл</strong>
                </div>
            </div>
            <div id="calendar" class="tab-pane">
                <div class="panel-body">
                    <strong>Тут календарь</strong>
                </div>
            </div>
            <div id="statistics" class="tab-pane">
                <div class="panel-body">
                    <strong>Тут статистика</strong>
                </div>
            </div>
            <div id="notification" class="tab-pane">
                <div class="panel-body">
                    <strong>Тут уведомления</strong>
                </div>
            </div>
        </div>
    </div>
</div>