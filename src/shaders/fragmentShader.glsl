#version 400 core

in vec2 pass_textureCoords;// texture coordinates passed in from vertex shader

out vec4 out_color;// output color

uniform sampler2D textureSampler;// take GL_TEXTURE0 and use it as the texture we are currently trying to render

void main(void){
    out_color = texture(textureSampler, pass_textureCoords);// output the color as the texture
}