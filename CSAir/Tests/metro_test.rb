require 'minitest/autorun'

# class which tests the Metro class
class MetroTest < Minitest::Test
  require_relative '../Graph/metro'
  # Called before every test method runs. Can be used
  # to set up fixture information.
  # Create a sample metro in the form of a hash
  def setup
    @metro = Hash.new
    @metro['code'] = 'ABC'
    @metro['name'] = 'Test'
    @metro['country'] = 'USA'
    @metro['continent'] = 'North America'
    @metro['timezone'] = 0
    @metro['coordinates'] = Hash.new
    @metro['coordinates']['N'] = 0
    @metro['coordinates']['W'] = 0
    @metro['population'] = 0
    @metro['region'] = 0
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
  end

  # pass the metro hash into the Metro constructor and assert
  # that values are copied properly
  def test_initialize
    metro = Metro.new(@metro)
    assert_equal(metro.country, @metro['country'])
    assert_instance_of(Metro, metro)
  end
end