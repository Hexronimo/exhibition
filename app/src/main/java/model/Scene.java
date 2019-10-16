package model;

public class Scene {

    private int id;
    private String imagePath;
    private Integer left;
    private Integer right;
    private String title;
    private String adminTitle;
    private String adminDesc;

    public Scene(String image, String title, String adminTitle, String adminDesc) {
        if(image != null) {
            this.imagePath = image;
        } else {
            this.imagePath = "@drawable/testscene";
        }
        this.title = title;
        if (adminTitle != null && adminTitle.trim().length() > 0) {
            this.adminTitle = adminTitle;
        } else {
            this.adminTitle = title;
        }
        this.adminDesc = adminDesc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLeft() {
        return left;
    }

    public Integer getRight() {
        return right;
    }

    public void LinkLeftRight(int leftSceneId, int rightSceneId) {
        this.left = leftSceneId;
        this.right = rightSceneId;
    }
}
