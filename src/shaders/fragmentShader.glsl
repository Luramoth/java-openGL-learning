#version 400 core

in vec2 pass_textureCoords;// texture coordinates passed in from vertex shader
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 out_color;// output color

uniform sampler2D textureSampler;// take GL_TEXTURE0 and use it as the texture we are currently trying to render

uniform vec3 lightColor;

void main(void){

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDotl = dot(unitNormal, unitLightVector);
    float brightness = max(nDotl, 0.0);
    vec3 diffuse = brightness * lightColor;

    out_color = vec4(diffuse, 1.0) *  texture(textureSampler, pass_textureCoords);// output the color as the texture
}