package com.wakeel.photoweather.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxEventBus {
    private static RxEventBus rxEventBus;
    private final Subject<Object, Object> subscriberSubjects = new SerializedSubject<>(PublishSubject.create());

    private RxEventBus(){

    }

    public static RxEventBus getEventBus(){
        if (rxEventBus == null){
            rxEventBus = new RxEventBus();
        }
        return rxEventBus;
    }

    //Send any object to bus i.e. emits data to subscribers
    public void sendToBus(Object o) {
        subscriberSubjects.onNext(o);
    }

    //get observable and subscribe anywhere
    public Observable<Object> getObservables(){
        return subscriberSubjects;
    }
}
