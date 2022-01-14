package com.ahmedsallam.pager

/**
 * Callback interface to pager index change
 */
interface OnIndexChangeListener {
    /**
     * Called on pager index changed
     * @param index New pager index
     */
    fun onIndexChanged(index: Int)
}