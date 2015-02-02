<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="addDishesModal"
     class="modal hide fade in centering insertAndUpdateDialogs"
     role="dialog"
     aria-labelledby="addDishesModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <h3 id="addDishesModalLabel" class="displayInLine">
            <spring:message code="create"/>&nbsp;<spring:message code="dish"/>
        </h3>
    </div>
    <div class="modal-body">
        <form name="newDishForm" novalidate >
            <div class="pull-left">
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="dishes.name"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="text"
                               required
                               autofocus
                               ng-model="dish.name"
                               name="name"
                               placeholder="<spring:message code='dish'/>&nbsp;<spring:message code='dishes.name'/>"/>
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && newDishForm.name.$error.required">
                                        <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="dishes.cook"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="text"
                               required
                               ng-model="dish.cook"
                               name="cook"
                               placeholder="<spring:message code='sample.cook'/> "/>
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && newDishForm.cook.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                
                <input type="submit"
                       class="btn btn-inverse"
                       ng-click="createDish(newDishForm);"
                       value='<spring:message code="create"/>'/>
                <button class="btn btn-inverse"
                        data-dismiss="modal"
                        ng-click="exit('#addDishesModal');"
                        aria-hidden="true">
                    <spring:message code="cancel"/>
                </button>
            </div>
        </form>
    </div>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorOnSubmit">
        <spring:message code="request.error"/>
    </span>
</div>

<div id="updateDishesModal"
     class="modal hide fade in centering insertAndUpdateDialogs"
     role="dialog"
     aria-labelledby="updateDishesModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <h3 id="updateDishesModalLabel" class="displayInLine">
            <spring:message code="update"/>&nbsp;<spring:message code="dish"/>
        </h3>
    </div>
    <div class="modal-body">
        <form name="updateDishForm" novalidate>
            <input type="hidden"
                   required
                   ng-model="dish.id"
                   name="id"
                   value="{{dish.id}}"/>

            <div class="pull-left">
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="dishes.name"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="text"
                               autofocus
                               required
                               ng-model="dish.name"
                               name="name"
                               placeholder="<spring:message code='dish'/>&nbsp;<spring:message code='dishes.name'/> "/>
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && updateDishForm.name.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="dishes.cook"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="text"
                               required
                               ng-model="dish.cook"
                               name="cook"
                               placeholder="<spring:message code='sample.cook'/> "/>
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && updateDishForm.cook.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <input type="submit"
                       class="btn btn-inverse"
                       ng-click="updateDish(updateDishForm);"
                       value='<spring:message code="update"/>'/>
                <button class="btn btn-inverse"
                        data-dismiss="modal"
                        ng-click="exit('#updateDishesModal');"
                        aria-hidden="true">
                    <spring:message code="cancel"/></button>
            </div>
        </form>
    </div>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorOnSubmit">
        <spring:message code="request.error"/>
    </span>
</div>

<div id="deleteDishesModal"
     class="modal hide fade in centering"
     role="dialog"
     aria-labelledby="searchDishesModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <h3 id="deleteDishesModalLabel" class="displayInLine">
            <spring:message code="delete"/>&nbsp;<spring:message code="dish"/>
        </h3>
    </div>
    <div class="modal-body">
        <form name="deleteDishForm" novalidate>
            <p><spring:message code="delete.confirm"/>:&nbsp;{{dish.name}}?</p>

            <input type="submit"
                   class="btn btn-inverse"
                   ng-click="deleteDish();"
                   value='<spring:message code="delete"/>'/>
            <button class="btn btn-inverse"
                    data-dismiss="modal"
                    ng-click="exit('#deleteDishesModal');"
                    aria-hidden="true">
                <spring:message code="cancel"/>
            </button>
        </form>
    </div>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorOnSubmit">
        <spring:message code="request.error"/>
    </span>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorIllegalAccess">
        <spring:message code="request.illegal.access"/>
    </span>
</div>

<div id="searchDishesModal"
     class="modal hide fade in centering"
     role="dialog"
     aria-labelledby="searchDishesModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <h3 id="searchDishesModalLabel" class="displayInLine">
            <spring:message code="search"/>
        </h3>
    </div>
    <div class="modal-body">
        <form name="searchDishForm" novalidate>
            <label><spring:message code="search.for"/></label>

            <div>
                <div class="input-append">
                    <input type="text"
                           autofocus
                           required
                           ng-model="searchFor"
                           name="searchFor"
                           placeholder="<spring:message code='dish'/>&nbsp;<spring:message code='dishes.name'/> "/>
                </div>
                <div class="input-append displayInLine">
                    <label class="displayInLine">
                        <span class="alert alert-error"
                              ng-show="displayValidationError && searchDishForm.searchFor.$error.required">
                            <spring:message code="required"/>
                        </span>
                    </label>
                </div>
            </div>
            <input type="submit"
                   class="btn btn-inverse"
                   ng-click="searchDish(searchDishForm, false);"
                   value='<spring:message code="search"/>'
                    />
            <button class="btn btn-inverse"
                    data-dismiss="modal"
                    ng-click="exit('#searchDishesModal');"
                    aria-hidden="true">
                <spring:message code="cancel"/>
            </button>
        </form>
    </div>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorOnSubmit">
        <spring:message code="request.error"/>
    </span>
</div>
