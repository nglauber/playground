package br.com.nglauber.exemplolivro.model.persistence

import br.com.nglauber.exemplolivro.model.persistence.web.PostWeb

class DataSourceFactory private  constructor () {

    companion object {
        fun getDefaultPostDataSource() : PostDataSource = PostWeb()//PostDb()//
    }
}