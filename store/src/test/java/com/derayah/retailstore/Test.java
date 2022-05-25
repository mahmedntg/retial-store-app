package com.derayah.retailstore;

import static java.util.Optional.ofNullable;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Test.
 *
 * @author : Mo Sayed
 * @since : 5/25/2022
 */
abstract class Test<T> {
    private MockMvc mvc;
    private String url;
    private String expectedResponse;
    private MockHttpServletRequestBuilder mockHttpServletRequestBuilder;
    private boolean ignoreResponseBody = false;
    private boolean checkResponseValues = true;
    private List<String> ignoredPaths = new ArrayList<>();
    private static final String HTTP_METHOD_ERROR = "You must build the http method first";

    /**
     * assertSuccessfulResponse.
     *
     * @throws Exception any knid of {@link Exception}
     */
    public void assertSuccessfulResponse() throws Exception {

        Objects.requireNonNull(expectedResponse, "You must first set the expected response");
        assertSuccessfulResponse(HttpStatus.OK);
    }

    /**
     * assert success response with a custom http status code.
     *
     * @param httpStatus {@link HttpStatus}
     * @throws Exception any knid of {@link Exception}
     */
    public void assertSuccessfulResponse(final HttpStatus httpStatus) throws Exception {

        MvcResult mvcResult = mvc.perform(mockHttpServletRequestBuilder).andDo(MockMvcResultHandlers.print())
            .andExpect(status().is(httpStatus.value())).andReturn();

        if (!ignoreResponseBody) {
            if (checkResponseValues) {
                assertResponseBody(expectedResponse, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
            } else {
                assertResponseStructure(expectedResponse, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
            }
        }
        clean();
    }

    /**
     * assert valid request exception.
     *
     * @throws Exception any kind of {@link Exception}
     */
    public void assertValidRequestDefaultStatusResponse() throws Exception {

        mvc.perform(mockHttpServletRequestBuilder)
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());
        clean();
    }

    /**
     * Asserts response code and body including all validationErrors.
     *
     * @throws Exception any exception thrown while calling the given endpoint
     */
    public void assertBadRequestResponseBody() throws Exception {

        final MvcResult mvcResult = mvc.perform(mockHttpServletRequestBuilder)
            .andExpect(status().isBadRequest())
            .andDo(MockMvcResultHandlers.print())
            .andReturn();

        if (!ignoreResponseBody) {
            if (checkResponseValues) {
                assertResponseBody(expectedResponse, mvcResult.getResponse().getContentAsString());
            } else {
                assertResponseStructure(expectedResponse, mvcResult.getResponse().getContentAsString());
            }
        }

        clean();
    }

    /**
     * The first method you have to call in order to build your UnitTest.
     *
     * @param url
     * @return
     */
    protected T url(final String url) {
        ofNullable(url)
            .map(u -> u.startsWith("/") ? url : "/" + url)
            .ifPresent(u -> this.url = u);
        return (T) this;
    }

    /**
     * init with GET http method.
     *
     * @return
     */
    public T methodGet() {
        return method(HttpMethod.GET);
    }

    /**
     * init with POST http method.
     *
     * @return
     */
    public T methodPost() {
        return method(HttpMethod.POST);
    }

    /**
     * init with given http method.
     *
     * @param httpMethod http method
     * @return
     */
    @SneakyThrows
    public T method(final HttpMethod httpMethod) {

        Objects.requireNonNull(httpMethod, "You must specify an Http method");
        Objects.requireNonNull(url, "You must build the url before building method type");

        switch (httpMethod) {

            case GET:
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(url);
                break;
            case POST:
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(url);
                break;
            case PUT:
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.put(url);
                break;
            case DELETE:
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.delete(url);
                break;
            case HEAD:
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.head(url);
                break;
            case OPTIONS:
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.options(url);
                break;
            case PATCH:
                mockHttpServletRequestBuilder = MockMvcRequestBuilders.patch(url);
                break;
            default:
                throw new RuntimeException("");
        }
        mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON);

        return (T) this;
    }

    /**
     * add a request header.
     *
     * @param headerName  header name value
     * @param headerValue headerName value
     * @return
     */
    public T header(final String headerName, final String headerValue) {

        Objects.requireNonNull(headerName, "You must specify the header name");
        Objects.requireNonNull(headerValue, "You must specify the header value");
        Objects.requireNonNull(mockHttpServletRequestBuilder, HTTP_METHOD_ERROR);

        //add header
        mockHttpServletRequestBuilder.header(headerName, headerValue);

        return (T) this;
    }


    /**
     * add query param to the request.
     *
     * @param name  query param name
     * @param value query param value
     * @return current UnitTest object
     */
    public T param(final String name, final String value) {

        Objects.requireNonNull(name, "You must specify the query parameter name");
        Objects.requireNonNull(name, "You must specify the query parameter value");

        mockHttpServletRequestBuilder.param(name, value);

        return (T) this;
    }

    /**
     * bodyFile.
     *
     * @param fileName body content file name
     * @return
     * @throws IOException Read file failed
     */
    public T bodyFile(final String fileName) throws IOException {

        Objects.requireNonNull(fileName, "You must specify the json request file name");
        Objects.requireNonNull(mockHttpServletRequestBuilder, HTTP_METHOD_ERROR);

        mockHttpServletRequestBuilder.content(readResourceFile(fileName));

        return (T) this;
    }

    /**
     * bodyContent.
     *
     * @param content Content
     * @return
     */
    public T bodyContent(final String content) {

        Objects.requireNonNull(content, "You must specify the json request file name");
        Objects.requireNonNull(mockHttpServletRequestBuilder, HTTP_METHOD_ERROR);
        mockHttpServletRequestBuilder.content(content);

        return (T) this;
    }

    /**
     * expectedResponse.
     *
     * @param response response
     * @return
     * @throws Exception any kind of {@link Exception}
     */
    public T expectedResponse(final String response) throws IOException {

        Objects.requireNonNull(response, "You must specify the json response file name");

        this.expectedResponse = response;

        return (T) this;
    }

    /**
     * assertResponseBody.
     *
     * @param expectedJsonResponse Expected JSON Response
     * @param contentAsString      Content As String
     * @throws JSONException {@link JSONException}
     */
    private void assertResponseBody(final String expectedJsonResponse, final String contentAsString) throws JSONException {

        // List of all customizations
        List<Customization> customizations = ignoredPaths.stream().map(path -> new Customization(path, ((o1, o2) -> true)))
            .collect(Collectors.toList());

        JSONAssert.assertEquals(expectedJsonResponse, contentAsString,
            new CustomComparator(JSONCompareMode.LENIENT, customizations.toArray(new Customization[] { })));
    }

    /**
     * assertResponseStructure.
     *
     * @param expectedResponse Expected Response
     * @param contentAsString  Content As String
     * @throws JSONException {@link JSONException}
     */
    private void assertResponseStructure(final String expectedResponse, final String contentAsString) throws JSONException {
        JSONAssert.assertEquals(expectedResponse, contentAsString, new JSONComparator() {
            @Override
            public JSONCompareResult compareJSON(final JSONObject expected, final JSONObject actual) throws JSONException {
                JSONArray names = expected.names();
                JSONCompareResult result = new JSONCompareResult();
                for (int i = 0; i < names.length(); i++) {
                    String field = names.getString(i);
                    if (!actual.has(field)) {
                        result.missing(field, field);
                    } else {
                        JSONObject expectedObject = expected.optJSONObject(field);
                        JSONObject actualObject = actual.optJSONObject(field);
                        if (expectedObject != null && actualObject != null) {
                            compareJSON(field, expectedObject, actualObject, result);
                        }
                    }
                }
                return result;
            }

            @Override
            public JSONCompareResult compareJSON(final JSONArray expected, final JSONArray actual) throws JSONException {
                return new JSONCompareResult();
            }

            @Override
            public void compareJSON(final String prefix, final JSONObject expected, final JSONObject actual, final JSONCompareResult result)
                throws JSONException {
                JSONArray names = expected.names();
                for (int i = 0; i < names.length(); i++) {
                    String field = names.getString(i);
                    if (!actual.has(field)) {
                        result.missing(field, field);
                    } else {
                        JSONObject expectedObject = expected.optJSONObject(field);
                        JSONObject actualObject = actual.optJSONObject(field);
                        if (expectedObject != null && actualObject != null) {
                            compareJSON(prefix + "." + field, expectedObject, actualObject, result);
                        }
                    }
                }
            }

            @Override
            public void compareValues(final String prefix, final Object expectedValue, final Object actualValue, final JSONCompareResult result)
                throws JSONException {

            }

            @Override
            public void compareJSONArray(final String prefix, final JSONArray expected, final JSONArray actual, final JSONCompareResult result)
                throws JSONException {

            }
        });
    }

    /**
     * Clean.
     */
    private void clean() {
        url = null;
        expectedResponse = null;
    }

    /**
     * Indicates if we need to ignore the json body (No body loading).
     *
     * @param ignore A boolean value if true the body will be ignored and won't be checked at all
     * @return This.
     */
    public T ignoreResponseBody(boolean ignore) {
        this.ignoreResponseBody = ignore;

        return (T) this;
    }

    /**
     * Set if the values of the response needs to be checked or just check for JSON structure.
     *
     * @param checkResponseValues A boolean value to indicate whether to check for the values or
     *                            just the JSON structure.
     * @return This.
     */
    public T checkResponseValues(boolean checkResponseValues) {
        this.checkResponseValues = checkResponseValues;

        return (T) this;
    }

    /**
     * Ignore {date} and {request_no} json paths.
     *
     * @return This  instance.
     */
    public T ignoreDefaultPaths() {
        ignorePaths("date");

        return (T) this;
    }

    /**
     * Ignore json paths.
     *
     * @param path The path to be ignored by the assertions.
     * @return This object.
     */
    public T ignorePath(final String path) {
        this.ignoredPaths.add(path);

        return (T) this;
    }

    /**
     * Ignore json paths.
     *
     * @param paths The paths to be ignored by the assertions.
     * @return This object.
     */
    public T ignorePaths(final String... paths) {
        ignoredPaths.addAll(Arrays.asList(paths));

        return (T) this;
    }

    /**
     * Ignore json paths.
     *
     * @param paths The paths to be ignored by the assertions.
     * @return This object.
     */
    public T ignorePaths(final Iterable<String> paths) {
        paths.forEach(ignoredPaths::add);

        return (T) this;
    }

    /**
     * Ignore json paths.
     *
     * @param paths The paths to be ignored by the assertions.
     * @return This object.
     */
    public T ignorePaths(final Collection<String> paths) {
        ignoredPaths.addAll(paths);

        return (T) this;
    }

    public void setMvc(final MockMvc mvc) {
        this.mvc = mvc;
    }

    public T requestWith(RequestPostProcessor postProcessor) {
        this.mockHttpServletRequestBuilder.with(postProcessor);
        return (T) this;
    }

    private String readResourceFile(final String resourceFileName) throws IOException {
        ClassPathResource cpr = new ClassPathResource(resourceFileName);
        byte[] data = FileCopyUtils.copyToByteArray(cpr.getInputStream());
        return new String(data, StandardCharsets.UTF_8);
    }
}