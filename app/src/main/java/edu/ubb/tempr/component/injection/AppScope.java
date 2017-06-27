package edu.ubb.tempr.component.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by zsoltszabo on 6/17/17.
 */

@Retention(RetentionPolicy.SOURCE)
@Scope
public @interface AppScope {
}
