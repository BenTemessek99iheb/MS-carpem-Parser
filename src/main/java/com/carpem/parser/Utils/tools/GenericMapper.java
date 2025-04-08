package com.carpem.parser.Utils.tools;

public interface GenericMapper<E, D> {

    E toModel(D dto);

    D toDto(E entity);
}
