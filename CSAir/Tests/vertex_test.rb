require 'minitest/autorun'
require_relative '../Graph/init'
include Init

# Class which tests the Vertex class
class VertexText < Minitest::Test
  require_relative '../Graph/metro'
  require_relative '../Graph/edge'
  require_relative '../Graph/vertex'
  require_relative '../Graph/graph'
  # Called before every test method runs. Can be used
  # to set up fixture information.
  # Create a blank sample vertex
  def setup
    blank_metro = ''
    @vertex = Vertex.new(blank_metro)
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
  end

  # assert that the vertex starts with 0 edges
  def test_initialize
    assert_equal(@vertex.edges.length, 0)
    assert_instance_of(Vertex, @vertex)
  end

  # assert that the edges array is updated properly
  def test_add_destination
    assert_equal(@vertex.edges.length, 0)
    @vertex.add_destination(Edge.new('', 4000))
    @vertex.add_destination(Edge.new('', 4000))
    @vertex.add_destination(Edge.new('', 4000))
    assert_equal(@vertex.edges.length, 3)
  end

  # assert that longest_flight returns the max distance
  def test_longest_flight
    @vertex.add_destination(Edge.new('', 3000))
    @vertex.add_destination(Edge.new('', 4000))
    @vertex.add_destination(Edge.new('', 1000))
    longest_flight = @vertex.longest_flight
    assert_equal(longest_flight.distance, 4000)
    assert_instance_of(Edge, longest_flight)
  end

  # assert that shortest_flight returns the min distance
  def test_shortest_flight
    @vertex.add_destination(Edge.new('', 3000))
    @vertex.add_destination(Edge.new('', 4000))
    @vertex.add_destination(Edge.new('', 1000))
    shortest_flight = @vertex.shortest_flight
    assert_equal(shortest_flight.distance, 1000)
    assert_instance_of(Edge, shortest_flight)
  end

  # assert layover time is a function of the number of connections
  def test_layover
    @vertex.add_destination(Edge.new('', 3000))
    layover = 2.166666667 - (0.166666667)
    assert_equal(@vertex.layover, layover)
    @vertex.add_destination(Edge.new('', 4000))
    @vertex.add_destination(Edge.new('', 1000))
    layover = 2.166666667 - (0.166666667 * 3)
    assert_equal(@vertex.layover, layover)
  end

  # assert that there is a route from santiago to lima
  def test_find_route_success
    @south_america = Init.set_up_map('../JSON/test_data.json')
    santiago = @south_america.vertices[@south_america.metros['santiago']]
    lima = @south_america.vertices[@south_america.metros['lima']]
    route = santiago.find_route(@south_america.metros['lima'])
    assert_equal(1, route.length)
    assert_instance_of(Array, route)
    assert_equal(route[0].vertex, lima)
  end

  # assert that there is no route from santiago to bogota
  def test_find_route_empty
    @south_america = Init.set_up_map('../JSON/test_data.json')
    santiago = @south_america.vertices[@south_america.metros['santiago']]
    route = santiago.find_route(@south_america.metros['bogota'])
    assert_equal(0, route.length)
    assert_instance_of(Array, route)
  end
end