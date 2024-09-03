public class CircularClassroom extends Classroom {
    private double radius;

    private CircularClassroom(Builder builder) {
        super(builder);
        this.radius = builder.radius;
    }

    public static class Builder extends Classroom.Builder<Builder> {
        private double radius;

        public Builder setRadius(double radius) {
            this.radius = radius;
            setWidth(radius * 2);
            setLength(radius * 2);
            return this;
        }

        @Override
        public CircularClassroom build() {

            setShape("Circle");
            return new CircularClassroom(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
