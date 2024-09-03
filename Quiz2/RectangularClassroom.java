public class RectangularClassroom extends Classroom {
    private RectangularClassroom(Builder builder) {
        super(builder);
    }

    public static class Builder extends Classroom.Builder<Builder> {
        public RectangularClassroom build() {
            setShape("Rectangle");
            return new RectangularClassroom(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }


    }
