package com.studiosheepdog.dognapper.board.domain.aggregate.enums;

public enum BoardCategory {
    FREE("자유게시판"), PHOTO("스크린샷");

    private final String displayName;

    BoardCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
