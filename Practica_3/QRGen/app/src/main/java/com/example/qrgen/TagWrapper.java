package com.example.qrgen;

class TagWrapper {
    private String id;
    TagTechList techList = new TagTechList();

    public TagWrapper(final String tagId) {
        id = tagId;
    }

    public final String getId() {
        return id;
    }
}
