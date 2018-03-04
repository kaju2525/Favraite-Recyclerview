package karun.com.recyclerviewfavraite


class Model {

    var id: Int = 0
    var title: String? = null
    var subTitle: String? = null


    constructor() : super() {}

    constructor(id: Int, title: String, subTitle: String) : super() {
        this.id = id
        this.title = title
        this.subTitle = subTitle
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj)
            return true
        if (obj == null)
            return false
        if (javaClass != obj.javaClass)
            return false
        val other = obj as Model?
        return id == other!!.id
    }
}
