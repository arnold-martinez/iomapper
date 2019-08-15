package io.dojogeek.parser.functions;

import io.dojogeek.parser.Callable;
import io.dojogeek.parser.Result;

import java.util.List;

/**
 * Created by norveo on 10/14/18.
 */
public class ToString implements Callable {

    @Override
    public Result invoke(String arguments) {
        Result result = new Result();
        result.setValue(arguments);

        return result;
    }

}
