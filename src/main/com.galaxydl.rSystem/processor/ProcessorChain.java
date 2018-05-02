package processor;

import bean.Request;
import bean.Response;

public class ProcessorChain implements Chain {
    private Processor head;

    @Override
    public final void add(Processor processor) {
        head.setNext(processor);
        head = processor;
    }

    @Override
    public final Response process(Request request) {
        Response response = new Response();
        if (head != null) {
            head.process(request, response);
        }
        return response;
    }
}
