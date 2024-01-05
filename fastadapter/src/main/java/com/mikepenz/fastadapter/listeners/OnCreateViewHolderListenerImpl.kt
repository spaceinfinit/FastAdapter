package com.mikepenz.fastadapter.listeners

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.IHookable
import com.mikepenz.fastadapter.IItemVHFactory
import com.mikepenz.fastadapter.utils.bind

/**
 * Default implementation of the OnCreateViewHolderListener
 */
open class OnCreateViewHolderListenerImpl<Item : GenericItem> : OnCreateViewHolderListener<Item> {
    /**
     * Is called inside the onCreateViewHolder method and creates the viewHolder based on the provided viewTyp
     *
     * @param parent   the parent which will host the View
     * @param viewType the type of the ViewHolder we want to create
     * @return the generated ViewHolder based on the given viewType
     */
    override fun onPreCreateViewHolder(fastAdapter: FastAdapter<Item>, parent: ViewGroup, viewType: Int, itemVHFactory: IItemVHFactory<*>): RecyclerView.ViewHolder {
        return itemVHFactory.getViewHolder(parent)
    }

    /**
     * Is called after the viewHolder was created and the default listeners were added
     *
     * @param viewHolder the created viewHolder after all listeners were set
     * @return the viewHolder given as param
     */
    override fun onPostCreateViewHolder(fastAdapter: FastAdapter<Item>, viewHolder: RecyclerView.ViewHolder, itemVHFactory: IItemVHFactory<*>): RecyclerView.ViewHolder {
        fastAdapter.eventHooks.bind(viewHolder)
        //check if the item implements hookable and contains event hooks
        (itemVHFactory as? IHookable<*>)?.eventHooks?.bind(viewHolder)
        return viewHolder
    }
}
