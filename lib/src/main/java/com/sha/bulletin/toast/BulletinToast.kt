package com.sha.bulletin.toast

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.sha.bulletin.*
import java.time.Duration
import java.util.concurrent.TimeUnit

class BulletinToast(context: Context): Toast(context), Bulletin {
    override val name: String = javaClass.name
    var options: Options = Options.default()

    override val content: String = options.content

    override fun dismiss() {
        cancel()
        bulletins.remove(this)
    }

    /**
     * Show this [Bulletin]
     */
    override fun show() {
        if (options.ignoreIfSameContentDisplayed && isBulletinDisplayed(name, content)) return
        super.show()
        bulletins.add(this)

        // schedule removing from bulletins
        val duration = if(duration == LENGTH_LONG) 3.5 else 2.toDouble()
        Handler().postDelayed({ bulletins.remove(this) },  TimeUnit.SECONDS.toMillis(duration.toLong()) )
    }

    data class Options(
            var content: String = "",
            var ignoreIfSameContentDisplayed: Boolean = BulletinConfig.ignoreIfSameContentDisplayed
    ){
        class Builder {
            private val options = Options()

            /**
             * If true, this [Bulletin] won't be displayed if there's another [Bulletin] displayed
             * with the same name and content of this [Bulletin]
             */
            fun ignoreIfSameContentDisplayed(ignore: Boolean): Builder {
                options.ignoreIfSameContentDisplayed = ignore
                return this
            }

            /**
             * Returns an instance of this options
             */
            fun build() = options
        }

        companion object {
            /**
             * Create the default options
             */
            fun default(): Options = Builder().build()
            /**
             * Create the options
             * @param block DSL for creating the options
             */
            fun create(block: Options.() -> Unit) = Options().apply(block)
        }
    }

    companion object {
        /**
         * Create the bulletin
         * @param block DSL for creating the options
         */
        fun create(context: Context, block: Options.() -> Unit): BulletinToast {
           return create(context, Options().apply(block), null)
        }

        /**
         * Create the bulletin
         * @param block DSL for creating the options
         */
        fun create(context: Context, options: Options): BulletinToast {
            return create(context, options, null)
        }

        /**
         * Create the bulletin
         * @param options for the bulletin
         */
        fun create(context: Context,
                   options: Options,
                   block: (BulletinToast.() -> Unit)?): BulletinToast {
           return BulletinToast(context).apply {
               view = LayoutInflater.from(context).inflate(R.layout.toast, null)
               view.findViewById<TextView>(R.id.message).text = options.content
               block?.invoke(this)
               this.options = options
           }
        }
    }


}