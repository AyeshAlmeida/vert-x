package io.ayesh.sample.service;

import io.ayesh.sample.domain.Whisky;

import java.util.List;

public interface WhiskeyService {
    List<Whisky> getAll();

    Whisky addOne(Whisky whisky);

    Whisky findOne(int id);

    Whisky update(Whisky updatedWhisky);

    void remove(int id);
}
