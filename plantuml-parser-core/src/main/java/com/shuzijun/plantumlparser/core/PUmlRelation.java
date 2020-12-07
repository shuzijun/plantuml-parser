package com.shuzijun.plantumlparser.core;

/**
 * class之间的关系
 *
 * @author 关系
 */
public class PUmlRelation {

    private String source;

    private String target;

    private String relation;

    public void setSource(String source) {
        this.source = source;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return source + " " + relation + " " + target;
    }
}
