#version 400 core

in vec3 position;// vertex positions for the model
in vec2 textureCoords;// texture UV's for the model

out vec2 pass_textureCoords;// output to pass streight to the fragment shader

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void){
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position.xyz,1.0);// converty the current vertex positions to a 4D coordinate
    pass_textureCoords = textureCoords;// immidiately pass the UV coordinates to the fragment shader as we cant really use them
}