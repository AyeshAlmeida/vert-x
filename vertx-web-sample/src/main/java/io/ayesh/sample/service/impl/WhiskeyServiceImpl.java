package io.ayesh.sample.service.impl;

import io.ayesh.sample.domain.Whisky;
import io.ayesh.sample.service.WhiskeyService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WhiskeyServiceImpl implements WhiskeyService {
    // Store our product
    private Map<Integer, Whisky> products = new LinkedHashMap<>();

    public WhiskeyServiceImpl() {
        Whisky bowmore = new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay");
        products.put(bowmore.getId(), bowmore);
        Whisky talisker = new Whisky("Talisker 57° North", "Scotland, Island");
        products.put(talisker.getId(), talisker);
    }

    @Override
    public List<Whisky> getAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Whisky addOne(Whisky whisky) {
        return products.put(whisky.getId(), whisky);
    }

    @Override
    public void remove(int id) {
        products.remove(id);
    }
}
