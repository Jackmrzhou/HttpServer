package org.gilmour.httpserver.views;

import org.gilmour.httpserver.models.HttpResponse;

public interface IView {
    HttpResponse toResponse();
}
