package com.springspecification.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class AllowBindWithoutFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest wrapped = new HttpServletRequestWrapper(req) {
            private Map<String, String[]> fieldsWithoutFilterName;

            private Map<String, String[]> map() {
                if (fieldsWithoutFilterName != null) return fieldsWithoutFilterName;
                fieldsWithoutFilterName = new LinkedHashMap<>(super.getParameterMap());
                Map<String, String[]> extra = new LinkedHashMap<>();
                for (var e : fieldsWithoutFilterName.entrySet()) {
                    String k = e.getKey();
                    if (k.startsWith("filter[") && k.endsWith("]")) {
                        String inner = k.substring("filter[".length(), k.length() - 1);
                        extra.put(inner, e.getValue());
                    }
                }
                fieldsWithoutFilterName.putAll(extra);
                return fieldsWithoutFilterName;
            }

            @Override public String getParameter(String name) { return Optional.ofNullable(map().get(name)).map(a -> a[0]).orElse(null); }
            @Override public Map<String, String[]> getParameterMap() { return map(); }
            @Override public Enumeration<String> getParameterNames() { return Collections.enumeration(map().keySet()); }
            @Override public String[] getParameterValues(String name) { return map().get(name); }
        };

        chain.doFilter(wrapped, res);
    }
}

