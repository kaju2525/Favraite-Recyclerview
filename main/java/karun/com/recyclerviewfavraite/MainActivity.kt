package karun.com.recyclerviewfavraite

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import karun.com.recyclerviewfavraite.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_posts_list.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var postsListAdapter: PostsListAdapter? = null
    private val postsModel = ArrayList<Model>()
    private lateinit var sharedPreference: SharedPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        sharedPreference = SharedPreference()
        postsModel.add(Model(1, "India", "New Delhi"))
        postsModel.add(Model(2, "United States", "Washington, D.C."))
        postsModel.add(Model(3, "Spain", "Madrid"))
        postsModel.add(Model(4, "Russia", "Moscow"))
        postsModel.add(Model(5, "Brazil", "Brasília"))
        postsModel.add(Model(6, "Germany", "Berlin"))
        postsModel.add(Model(7, "France", "Paris"))
        postsModel.add(Model(8, "Ireland", "Dublin"))
        postsModel.add(Model(9, "Italy", "Rome"))
        postsModel.add(Model(10, "Japan", "Tokyo"))

        postsListAdapter = PostsListAdapter(postsModel)
        posts_list.adapter = postsListAdapter
    }

    public override fun onResume() {
        super.onResume()
        postsListAdapter!!.notifyDataSetChanged()
    }


    inner class PostsListAdapter(private var postBeanSampleList: ArrayList<Model>) : BaseAdapter() {
        private var sharedPreference: SharedPreference = SharedPreference()

        private inner class ViewHolder {
            internal var txtTitle: TextView? = null
            internal var txtSubTitle: TextView? = null
            internal var btnFavourite: ImageView? = null
        }

        override fun getCount(): Int {
            return postBeanSampleList.size
        }

        override fun getItem(position: Int): Any {
            return postBeanSampleList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            val holder: ViewHolder
            if (convertView == null) {
                val inflater = baseContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                convertView = inflater.inflate(R.layout.item_posts_list, parent, false)
                holder = ViewHolder()
                holder.txtTitle = convertView!!.txtPostTitle
                holder.txtSubTitle = convertView.txtPostSubTitle
                holder.btnFavourite = convertView.favouritesToggle
                convertView.tag = holder
            } else {
                holder = convertView.tag as ViewHolder
            }
            val beanSampleList = getItem(position) as Model
            holder.txtTitle!!.text = beanSampleList.title
            holder.txtSubTitle!!.text = beanSampleList.subTitle


            if (checkFavoriteItem(beanSampleList)) {
                holder.btnFavourite!!.setImageResource(R.drawable.ic_favorite)
                holder.btnFavourite!!.tag = "active"
            } else {
                holder.btnFavourite!!.setImageResource(R.drawable.ic_favorite_outline)
                holder.btnFavourite!!.tag = "deactive"
            }
            holder.btnFavourite!!.setOnClickListener {
                val tag = holder.btnFavourite!!.tag.toString()
                if (tag.equals("deactive", ignoreCase = true)) {
                    sharedPreference.addFavorite(baseContext, postBeanSampleList[position])
                    holder.btnFavourite!!.tag = "active"
                    holder.btnFavourite!!.setImageResource(R.drawable.ic_favorite)
                } else {
                    sharedPreference.removeFavorite(baseContext, postBeanSampleList[position])
                    holder.btnFavourite!!.tag = "deactive"
                    holder.btnFavourite!!.setImageResource(R.drawable.ic_favorite_outline)
                }
            }
            return convertView
        }


        private fun checkFavoriteItem(checkProduct: Model): Boolean {
            var check = false
            val favorites = sharedPreference.loadFavorites(baseContext)
            if (favorites != null) {
                for (product in favorites) {
                    if (product == checkProduct) {
                        check = true
                        break
                    }
                }
            }
            return check
        }
    }
}
