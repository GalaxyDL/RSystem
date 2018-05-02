package handler;

import bean.Request;
import bean.Response;
import processor.Chain;
import processor.ProcessorChain;

public class RealRequestHandler implements RequestHandler {
    private Chain chain;

    RealRequestHandler() {
        this.chain = new ProcessorChain();
    }

    @Override
    public Response handle(Request request) {
        buildChain(request);
        return chain.process(request);
    }

    private void buildChain(Request request) {

    }

}
