package com.water.pos.injector;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Created by water on 14-12-19.
 */
public class MyModule implements Module {
    @Override
    public void configure(Binder binder) {
//        binder.bind(IPromotion.class).to(DiscountPromotion.class);
        binder.bind(HelloGuice.class).to(HelloGuiceImpl.class);
    }
}
