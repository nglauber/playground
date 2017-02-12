package br.com.nglauber.exemplolivro

import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import br.com.nglauber.exemplolivro.model.data.Post
import br.com.nglauber.exemplolivro.model.persistence.sqlite.DbHelper
import br.com.nglauber.exemplolivro.model.persistence.sqlite.PostDb
import br.com.nglauber.exemplolivro.model.persistence.sqlite.PostTable
import junit.framework.Assert.*
import org.jetbrains.anko.db.delete
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.*

@RunWith(AndroidJUnit4::class)
class DataBaseTest {

    var db : PostDb? = null

    @Before
    fun setup() {
        db = PostDb(DbHelper(InstrumentationRegistry.getTargetContext()))
    }

    @Test
    fun testCrud() {
        db?.dbHelper?.use {
            delete(PostTable.TABLE_NAME)

            var post : Post = Post(0L, "Texto", Date(), "content://media/external/images/media/24490")
            assertNotNull(post)
            assertEquals(db?.savePost(post), true)
            assertTrue(post.id > 0)
            var file = File(Uri.parse(post.photoUrl).path)
            assertTrue(file.exists())
            post.text = "Texto novo"
            assertTrue(db?.savePost(post)!!)

            post = db?.loadPost(post.id)!!
            assertNotNull(post)
            assertEquals(post.text, "Texto novo")

            assertTrue(db?.deletePost(post)!!)
            file = File(Uri.parse(post.photoUrl).path)
            assertFalse(file.exists())
        }
    }
}