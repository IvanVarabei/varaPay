package com.epam.varapay.controller.router;

public class Router {
    private String page;
    private RouterType type = RouterType.FORWARD;

    public Router() {
    }

    public Router(String page, RouterType type) {
        this.page = page;
        this.type = type;
    }

    public Router(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public RouterType getType() {
        return type;
    }

    public void setRedirect(String page) {
        type = RouterType.REDIRECT;
        this.page = page;
    }

    public void setForward(String page) {
        type = RouterType.FORWARD;
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Router router = (Router) o;
        if (page != null ? !page.equals(router.page) : router.page != null) {
            return false;
        }
        return type == router.type;
    }

    @Override
    public int hashCode() {
        int result = page != null ? page.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}