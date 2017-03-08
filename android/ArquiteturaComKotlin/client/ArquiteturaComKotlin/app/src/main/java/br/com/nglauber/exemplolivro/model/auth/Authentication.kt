package br.com.nglauber.exemplolivro.model.auth

interface Authentication {

    fun startAuthProcess(l: OnAuthRequestedListener)

    //TODO check how to improve this last parameter
    fun handleAuthResponse(requestCode: Int, resultCode: Int, data: Any)

    companion object {
        val TYPE_GOOGLE = 0
        val TYPE_FACEBOOK = 1
    }
}
