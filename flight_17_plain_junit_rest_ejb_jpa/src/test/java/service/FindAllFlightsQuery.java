package service;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Stream;

public class FindAllFlightsQuery implements Query {
    private Stream<Object> results;

    public FindAllFlightsQuery(Stream<Object> results) {
        this.results = results;
    }

    @Override
    public List getResultList() {
        return results.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public Object getSingleResult() {
        return null;
    }

    @Override
    public int executeUpdate() {
        return 0;
    }

    @Override
    public Query setMaxResults(int maxResult) {
        return null;
    }

    @Override
    public int getMaxResults() {
        return 0;
    }

    @Override
    public Query setFirstResult(int startPosition) {
        return null;
    }

    @Override
    public int getFirstResult() {
        return 0;
    }

    @Override
    public Query setHint(String hintName, Object value) {
        return null;
    }

    @Override
    public Map<String, Object> getHints() {
        return null;
    }

    @Override
    public <T> Query setParameter(Parameter<T> param, T value) {
        return null;
    }

    @Override
    public Query setParameter(Parameter<Calendar> param, Calendar value, TemporalType temporalType) {
        return null;
    }

    @Override
    public Query setParameter(Parameter<Date> param, Date value, TemporalType temporalType) {
        return null;
    }

    @Override
    public Query setParameter(String name, Object value) {
        return null;
    }

    @Override
    public Query setParameter(String name, Calendar value, TemporalType temporalType) {
        return null;
    }

    @Override
    public Query setParameter(String name, Date value, TemporalType temporalType) {
        return null;
    }

    @Override
    public Query setParameter(int position, Object value) {
        return null;
    }

    @Override
    public Query setParameter(int position, Calendar value, TemporalType temporalType) {
        return null;
    }

    @Override
    public Query setParameter(int position, Date value, TemporalType temporalType) {
        return null;
    }

    @Override
    public Set<Parameter<?>> getParameters() {
        return null;
    }

    @Override
    public Parameter<?> getParameter(String name) {
        return null;
    }

    @Override
    public <T> Parameter<T> getParameter(String name, Class<T> type) {
        return null;
    }

    @Override
    public Parameter<?> getParameter(int position) {
        return null;
    }

    @Override
    public <T> Parameter<T> getParameter(int position, Class<T> type) {
        return null;
    }

    @Override
    public boolean isBound(Parameter<?> param) {
        return false;
    }

    @Override
    public <T> T getParameterValue(Parameter<T> param) {
        return null;
    }

    @Override
    public Object getParameterValue(String name) {
        return null;
    }

    @Override
    public Object getParameterValue(int position) {
        return null;
    }

    @Override
    public Query setFlushMode(FlushModeType flushMode) {
        return null;
    }

    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    @Override
    public Query setLockMode(LockModeType lockMode) {
        return null;
    }

    @Override
    public LockModeType getLockMode() {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return null;
    }
}
