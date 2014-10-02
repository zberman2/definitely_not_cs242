require 'minitest/autorun'

# Class which tests the Edge class
class EdgeTest < Minitest::Test
  require_relative '../Graph/metro'
  require_relative '../Graph/vertex'
  require_relative '../Graph/edge'
  # Called before every test method runs. Can be used
  # to set up fixture information.
  def setup
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
  end

  # create an edge and assert that it holds the correct distance
  # and is an instance of an Edge
  def test_initialize
    @edge = Edge.new('', 5000)
    assert_equal(@edge.distance, 5000)
    assert_instance_of(Edge, @edge)
  end
end