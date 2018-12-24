material {
    name = "test-material"
    vertexShaderSource = """
        #version 330 core
        layout (location = 0) in vec3 aPos;

        void main()
        {
            gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
        }
    """
    fragmentShaderSource = """
        #version 330 core

        out vec4 color;

        void main()
        {
            color = vec4(0.4, 0.3, 0.6, 1.0);
        }
    """
}