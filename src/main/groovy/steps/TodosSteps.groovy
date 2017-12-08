package steps

import apiResources.TodosResource
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TodosSteps {

    private static final Logger log = LoggerFactory.getLogger(TodosSteps.class.getName());

    static getAllTodos() {
        log.info("getting all todos")
        return TodosResource.todos_get()
    }
}
