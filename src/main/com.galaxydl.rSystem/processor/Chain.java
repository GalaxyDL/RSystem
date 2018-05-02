package processor;

import bean.Request;
import bean.Response;

public interface Chain {

    void add(Processor processor);

    Response process(Request request);
}
