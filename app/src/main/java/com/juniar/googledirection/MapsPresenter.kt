package com.juniar.googledirection

import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import juniar.porkat.Utils.NetworkApi
import juniar.porkat.Utils.NetworkManager

class MapsPresenter(val view: MapsView) {
    val compositeDisposable = CompositeDisposable()

    fun getDirection(from: LatLng, to: LatLng) {
        compositeDisposable.add(NetworkManager.createService(NetworkApi::class.java)
                .getDirection(origin = "${from.latitude},${from.longitude}", destination = "${to.latitude},${to.longitude}")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view.onGetDirection(false, it, null)
                }, {
                    view.onGetDirection(true, null, it)
                }))
    }

    fun onDestroyPresenter() {
        compositeDisposable.clear()
    }
}