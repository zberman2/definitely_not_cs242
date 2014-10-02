require 'minitest/autorun'

# Class which tests the Vertex class
class VertexText < Minitest::Test
  require_relative '../Graph/metro'
  require_relative '../Graph/edge'
  require_relative '../Graph/vertex'
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
end