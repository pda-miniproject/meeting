class HobbyVO {
    private int hobbyId;
    private String hobbyName;

    public HobbyVO(int hobbyId, String hobbyName) {
        this.hobbyId = hobbyId;
        this.hobbyName = hobbyName;
    }

    public int getHobbyId() {
        return hobbyId;
    }

    public String getHobbyName() {
        return hobbyName;
    }
}