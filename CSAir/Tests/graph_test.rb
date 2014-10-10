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
    @south_america = Init.set_up_map('../JSON/test_data.json')
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

  # assert that only South America is represented in this test
  def test_list_continents
    continents = "South America\n"
    assert_equal(continents, @south_america.list_continents)
  end

  # asserts that a single route follows the edge time method
  def test_time_single_route
    santiago = @south_america.vertices[@south_america.metros['santiago']]
    lima = @south_america.vertices[@south_america.metros['lima']]
    route = santiago.find_route(lima.metro.code)
    assert_equal(@south_america.time(route), route[0].time)
  end

  # asserts that layover times are factored into time calculations
  def test_time_multiple_routes
    edges = Array.new
    santiago = @south_america.vertices[@south_america.metros['santiago']]
    lima = @south_america.vertices[@south_america.metros['lima']]
    bogota = @south_america.vertices[@south_america.metros['bogota']]
    route_1 = santiago.find_route(lima.metro.code)[0]
    edges << route_1
    route_2 = lima.find_route(bogota.metro.code)[0]
    edges << route_2
    time = route_1.time + route_2.time + lima.layover
    assert_equal(@south_america.time(edges), time)
  end

  # assert that edges add up properly
  def test_distance
    edges = Array.new
    santiago = @south_america.vertices[@south_america.metros['santiago']]
    lima = @south_america.vertices[@south_america.metros['lima']]
    bogota = @south_america.vertices[@south_america.metros['bogota']]
    route_1 = santiago.find_route(lima.metro.code)[0]
    edges << route_1
    route_2 = lima.find_route(bogota.metro.code)[0]
    edges << route_2
    distance = route_1.distance + route_2.distance
    assert_equal(@south_america.distance(edges), distance)
  end

  # assert that each leg is cheaper per km
  def test_cost
    edges = Array.new
    santiago = @south_america.vertices[@south_america.metros['santiago']]
    lima = @south_america.vertices[@south_america.metros['lima']]
    bogota = @south_america.vertices[@south_america.metros['bogota']]
    route_1 = santiago.find_route(lima.metro.code)[0]
    edges << route_1
    route_2 = lima.find_route(bogota.metro.code)[0]
    edges << route_2
    cost = route_1.distance * 0.35
    cost += route_2.distance * 0.3
    assert_equal(@south_america.cost(edges), cost)
  end

  # test remove metro functionality
  def test_remove_metro
    @south_america.remove_metro('santiago')
    assert_equal(nil, @south_america.vertices[@south_america.metros['santiago']])

    # make sure lima can't get to santiago anymore
    lima = @south_america.vertices[@south_america.metros['lima']]
    assert_equal(0, lima.find_route('SCL'.to_sym).length)
  end

  # assert that we can delete a route, but the other way is still there
  def test_remove_route
    santiago = @south_america.vertices[@south_america.metros['santiago']]
    lima = @south_america.vertices[@south_america.metros['lima']]
    ports = %w(santiago lima)
    @south_america.remove_route(ports)
    assert_equal(0, santiago.find_route('LIM'.to_sym).length)
    assert_equal(1, lima.find_route('SCL'.to_sym).length)
  end

  # test add metro functionality
  def test_add_metro
    metro_hash = Hash.new
    metro_hash['code'] = 'ABC'
    metro_hash['name'] = 'test'
    metro_hash['country'] = 'US'
    metro_hash['continent'] = 'North America'
    metro_hash['timezone'] = 5
    metro_hash['coordinates'] = {'N' => 40 , 'W' => 45}
    metro_hash['population'] = 4000000
    metro_hash['region'] = 2
    # wasn't there before
    assert_equal(nil, @south_america.vertices['ABC'.to_sym])

    @south_america.add_metro(metro_hash)
    # is there now
    assert_equal('test', @south_america.vertices['ABC'.to_sym].metro.name)
  end

  # test add route functionality
  # only goes one way
  def test_add_route
    santiago = @south_america.vertices[@south_america.metros['santiago']]
    bogota = @south_america.vertices[@south_america.metros['bogota']]

    assert_equal(0, santiago.find_route('BOG'.to_sym).length)

    @south_america.add_route_with_distance('SCL'.to_sym, 'BOG'.to_sym, 5000)
    assert_equal(1, santiago.find_route('BOG'.to_sym).length)
    assert_equal(0, bogota.find_route('SCL'.to_sym).length)
  end
end