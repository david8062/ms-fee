package com.IusCloud.msFees.shared.responses;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse<T> {
    private final List<T> data;
    private final int total;

    public ListResponse(List<T> data) {
        this.data = data;
        this.total = data.size();
    }
}
