package hu.ait.android.weathermap

interface ItemTouchHelperAdapter{
    fun onDismissed(position: Int)
    fun onItemMoved(fromPosition: Int, toPosition: Int)
}