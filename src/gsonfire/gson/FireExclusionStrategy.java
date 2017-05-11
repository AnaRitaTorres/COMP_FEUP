package gsonfire.gson;

import gsonfire.postprocessors.methodinvoker.MappedMethod;

/**
 * Created by julio on 5/25/15.
 */
public interface FireExclusionStrategy {

    boolean shouldSkipMethod(MappedMethod method);

}
