package es.uv.uvlive.ui.actions;


import es.uv.uvlive.session.BusinessError;

public interface BaseActions {
    void onError(BusinessError businessError);
}
