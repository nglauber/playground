package br.com.nglauber.exemplolivro.model.auth;

public interface OnAuthRequestedListener {
    void onAuthSuccess();
    void onAuthCancel();
    void onAuthError();
}
