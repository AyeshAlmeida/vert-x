package io.ayesh.sample.service;

import io.ayesh.sample.domain.Whisky;

import java.util.List;

public interface WhiskeyService {
    List<Whisky> getAll();

    Whisky addOne(Whisky whisky);

    void remove(int id);
}
