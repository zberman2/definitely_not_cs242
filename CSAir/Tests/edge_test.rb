require 'minitest/autorun'

# Class which tests the Edge class
class EdgeTest < Minitest::Test
  require_relative '../Graph/metro'
  require_relative '../Graph/vertex'
  require_relative '../Graph/edge'
  # Called before every test method runs. Can be used
  # to set up fixture information.
  def setup
    @long_edge = Edge.new('', 5000)
    @short_edge = Edge.new('', 300)
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
  end

  # create an edge and assert that it holds the correct distance
  # and is an instance of an Edge
  def test_initialize
    assert_equal(@long_edge.distance, 5000)
    assert_instance_of(Edge, @long_edge)
  end

  # make sure short routes follow the special <400 km case
  def test_short_time
    short_time = 2 * Math.sqrt((300/2.0) / 703.125)
    assert_equal(short_time, @short_edge.time)
  end

  # make sure long routes follow the >400 km case
  def test_long_time
    long_time = 1.0677777 + ((5000 - 400) / 750.0)
    assert_equal(long_time, @long_edge.time)
  end
end