class ProfileDTO {
    private String userId;
    private String nickname;
    private float rating;
    private String gender;
    private String mbti;
    private float height;
    private float weight;

    public ProfileDTO( String nickname, float rating, String gender, String mbti, float height, float weight) {
        this.nickname = nickname;
        this.rating = rating;
        this.gender = gender;
        this.mbti = mbti;
        this.height = height;
        this.weight = weight;
    }

    public String getNickname() {
        return nickname;
    }

    public float getRating() {
        return rating;
    }

    public String getGender() {
        return gender;
    }

    public String getMbti() {
        return mbti;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }
}
