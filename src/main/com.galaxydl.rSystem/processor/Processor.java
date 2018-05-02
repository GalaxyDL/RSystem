package processor;

import bean.Request;
import bean.Response;

public abstract class Processor {
    private Processor next;

    public void process(Request request, Response response) {
        if (next != null) {
            next.process(request, response);
        }
    }

    public final void setNext(Processor processor) {
        next = processor;
    }

}
