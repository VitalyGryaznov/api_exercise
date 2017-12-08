package apiResources

class TodosResource extends BaseResource {

    final static TODOS_ENDPOINT = "todos"

    def static todos_get() {
        return getRes(TODOS_ENDPOINT)
    }
}
