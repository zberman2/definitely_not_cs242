require 'minitest/autorun'
require_relative '../Graph/init'
include Init

class GraphTest < Minitest::Test
  require_relative '../Graph/metro'
  require_relative '../Graph/edge'
  require_relative '../Graph/vertex'
  require_relative '../Graph/graph'


  # rubygems and json used for parsing map_data.json
  require 'rubygems'
  require 'json'

  # Called before every test method runs. Can be used
  # to set up fixture information.
  # initialize a map of South America
  # setup strings for countries that we will find throughout
  # the class to save space
  def setup
    @south_america = Init.set_up_map('../Tests/test_data.json')
    @santiago = "Code: SCL\n"
    @santiago << "Name: Santiago\n"
    @santiago << "Country: CL\n"
    @santiago << "Continent: South America\n"
    @santiago << "Timezone: -4\n"
    @santiago << "Coordinates: S: 33 W: 71\n"
    @santiago << "Population: 6000000\n"
    @santiago << 'Region: 1'

    @bogota = "Code: BOG\n"
    @bogota << "Name: Bogota\n"
    @bogota << "Country: CO\n"
    @bogota << "Continent: South America\n"
    @bogota << "Timezone: -5\n"
    @bogota << "Coordinates: N: 5 W: 74\n"
    @bogota << "Population: 8600000\n"
    @bogota << 'Region: 1'

    @buenos_aires = "Code: BUE\n"
    @buenos_aires << "Name: Buenos Aires\n"
    @buenos_aires << "Country: AR\n"
    @buenos_aires << "Continent: South America\n"
    @buenos_aires << "Timezone: -3\n"
    @buenos_aires << "Coordinates: S: 35 W: 58\n"
    @buenos_aires << "Population: 13300000\n"
    @buenos_aires << 'Region: 1'

    @sao_paulo = "Code: SAO\n"
    @sao_paulo << "Name: Sao Paulo\n"
    @sao_paulo << "Country: BR\n"
    @sao_paulo << "Continent: South America\n"
    @sao_paulo << "Timezone: -3\n"
    @sao_paulo << "Coordinates: S: 24 W: 47\n"
    @sao_paulo << "Population: 20900000\n"
    @sao_paulo << 'Region: 1'
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
  end

  # assert that set_up_map returns a Graph
  def test_set_up_map
    assert_instance_of(Graph, @south_america)
  end

  # assert the map has the right set of cities
  def test_list_metros
    metros = "Santiago\n"
    metros << "Lima\n"
    metros << "Bogota\n"
    metros << "Buenos Aires\n"
    metros << "Sao Paulo\n"

    assert_equal(metros, @south_america.list_metros)
  end

  # assert that we can properly access info about a city
  # also tests adjacent metros
  def test_metro_info
    info = @santiago
    info << "\n\nConnections:\n"
    info << "Lima, distance: 2453 km\n"

    assert_equal(info, @south_america.metro_info('santiago'))
  end

  # assert that longest_flight returns the max distance flight
  # in the network of flights
  def test_longest_flight
    # two options since order doesn't matter in longest flight
    info1 = @bogota
    info1 << "\n\nto\n\n"
    info1 << @buenos_aires
    info1 << "\n\nDistance: 4651 km"

    info2 = @buenos_aires
    info2 << "\n\nto\n\n"
    info2 << @bogota
    info2 << "\n\nDistance: 4651 km"

    assert_equal(info1 || info2, @south_america.longest_flight)
  end

  # assert that shortest_flight returns the min distance flight
  # in the network of flights
  def test_shortest_flight
    # two options since order doesn't matter in shortest flight
    info1 = @buenos_aires
    info1 << "\n\nto\n\n"
    info1 << @sao_paulo
    info1 << "\n\nDistance: 1680 km"

    info2 = @sao_paulo
    info2 << "\n\nto\n\n"
    info2 << @buenos_aires
    info2 << "\n\nDistance: 1680 km"

    assert_equal(info1 || info2, @south_america.shortest_flight)
  end

  # assert that average_flight computes the average of
  # all flight distances
  def test_average_flight
    average = (2453 + 1879 + 4323 + 4651 + 1680) / 5
    output = "Average Distance: #{average} km"
    assert_equal(output, @south_america.average_flight)
  end

  # assert that biggest_metro computes the max population city
  def test_biggest_metro
    info = @sao_paulo
    assert_equal(info, @south_america.biggest_metro)
  end
  # assert that smallest_metro computes the min population city
  def test_smallest_metro
    info = @santiago
    assert_equal(info, @south_america.smallest_metro)
  end

  # assert that average_metro computes the avg population city
  def test_average_metro
    average = (6000000 + 9050000 + 8600000 + 13300000 + 20900000) / 5
    output = "Average Population: #{average}"
    assert_equal(output, @south_america.average_metro)
  end

  # assert that only South America is represented in this test
  def test_list_continents
    continents = "South America\n"
    assert_equal(continents, @south_america.list_continents)
  end

  # assert that Bogota has the most connections out of any of
  # the South American cities in this map
  def test_hub_metros
    info = @bogota
    info << "\nNumber of connections: 3\n"

    assert_equal(info, @south_america.hub_metros(1))
  end
end