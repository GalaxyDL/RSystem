package handler;

import bean.Request;
import bean.Response;

public interface RequestHandler {

    Response handle(Request request);
}
